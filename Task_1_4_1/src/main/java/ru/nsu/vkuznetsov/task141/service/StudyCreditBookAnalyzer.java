package ru.nsu.vkuznetsov.task141.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.vkuznetsov.task141.model.ControlType;
import ru.nsu.vkuznetsov.task141.model.CreditMark;
import ru.nsu.vkuznetsov.task141.model.CreditStudyRecord;
import ru.nsu.vkuznetsov.task141.model.Grade;
import ru.nsu.vkuznetsov.task141.model.GradedStudyRecord;
import ru.nsu.vkuznetsov.task141.model.StudyCreditBook;
import ru.nsu.vkuznetsov.task141.model.StudyRecord;

/**
 * Утилитный сервис для анализа успеваемости студента. Содержит бизнес-правила для расчета
 * стипендий, дипломов и переводов.
 */
public final class StudyCreditBookAnalyzer {

    private static final double PERCENTAGE_OF_EXCELLENT = 0.75;
    private static final double NO_GRADES = 0.0;
    private static final int REQUIRED_SESSIONS = 2;
    private static final int FIRST_SEMESTER = 1;

    private StudyCreditBookAnalyzer() {

    }

    /**
     * Вычисляет текущий средний балл за все время обучения. Учитываются только записи типа
     * {@link GradedStudyRecord} (экзамены, диф. зачеты, ВКР). Обычные зачеты
     * ({@link CreditStudyRecord}) автоматически игнорируются.
     *
     * @param book зачетная книжка студента
     * @return средний балл, либо 0.0, если нет ни одной числовой оценки
     */
    public static double calculateAverageGrade(StudyCreditBook book) {
        long totalGrade = 0;
        long gradesCnt = 0;
        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            for (var studyRecord : book.getRecordsForSemester(i)) {
                if (studyRecord instanceof GradedStudyRecord graded) {
                    totalGrade += graded.grade().getNumericValue();
                    gradesCnt++;
                }
            }
        }

        // Возвращаем ноль, если нет оценок.
        return gradesCnt == 0 ? NO_GRADES : (double) totalGrade / gradesCnt;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре. Правило: все
     * экзамены и диф. зачеты за ПРЕДЫДУЩИЙ семестр должны быть сданы на "Отлично", а все обычные
     * зачеты должны быть сданы (PASSED).
     *
     * @param book зачетная книжка студента
     * @return true, если студент имеет право на повышенную стипендию
     */
    public static boolean canGetIncreasedStudentship(StudyCreditBook book) {
        int previousSemester = book.getCurrentSemester() - 1;
        if (previousSemester < FIRST_SEMESTER) {
            return false;
        }

        List<StudyRecord> records = book.getRecordsForSemester(previousSemester);
        if (records.isEmpty()) {
            return false;
        }
        for (var studyRecord : records) {
            if (studyRecord instanceof CreditStudyRecord credit
                && credit.mark() != CreditMark.PASSED) {
                return false;
            }
            if (studyRecord instanceof GradedStudyRecord graded
                && graded.grade() != Grade.EXCELLENT) {
                return false;
            }
        }

        return true;
    }

    /**
     * Прогнозирует возможность получения диплома с отличием ("красного" диплома). Правила: 1.
     * Отсутствие итоговых оценок "Удовлетворительно" и ниже. 2. Не менее 75% итоговых оценок
     * (последняя оценка по каждому предмету) — "Отлично". 3. Квалификационная работа (ВКР) защищена
     * на "Отлично" (если защита уже состоялась).
     *
     * @param book зачетная книжка студента
     * @return true, если студент сохраняет шансы на красный диплом
     */
    public static boolean canGetRedDiploma(StudyCreditBook book) {
        Map<String, Grade> finalGrades = new HashMap<>();
        Grade defense = null;
        for (int semester = FIRST_SEMESTER; semester <= book.getCurrentSemester(); semester++) {
            for (var record : book.getRecordsForSemester(semester)) {
                if (record instanceof GradedStudyRecord graded) {
                    if (graded.controlType() == ControlType.VKR_DEFENSE) {
                        defense = graded.grade();
                    } else {
                        finalGrades.put(graded.subjectName(), graded.grade());
                    }
                }
            }
        }
        if (defense != null && defense != Grade.EXCELLENT) {
            return false;
        }
        int excellent = defense == null ? 0 : 1;
        int total = finalGrades.size() + excellent;
        for (Grade grade : finalGrades.values()) {
            if (grade == Grade.SATISFACTORY || grade == Grade.UNSATISFACTORY) {
                return false;
            }
            if (grade == Grade.EXCELLENT) {
                excellent++;
            }
        }
        return total > 0 && excellent >= total * PERCENTAGE_OF_EXCELLENT;
    }

    /**
     * Проверяет две последние внесённые сессии платника. Тройка допускается за дифзачёт.
     * Двойка или несданный зачёт означают незакрытую сессию.
     *
     * @param book зачетная книжка студента
     * @return true, если студент имеет право подать заявление на перевод
     */
    public static boolean canTransferToBudget(StudyCreditBook book) {
        if (!book.isPaidEducation()) {
            return false;
        }
        int sessions = 0;
        for (int semester = book.getCurrentSemester(); semester >= FIRST_SEMESTER; semester--) {
            List<StudyRecord> records = book.getRecordsForSemester(semester);
            if (records.isEmpty()) {
                continue;
            }
            for (var record : records) {
                if (record instanceof CreditStudyRecord credit
                        && credit.mark() == CreditMark.FAILED) {
                    return false;
                }
                if (record instanceof GradedStudyRecord graded) {
                    if (graded.grade() == Grade.UNSATISFACTORY
                            || (graded.controlType() == ControlType.EXAM
                            && graded.grade() == Grade.SATISFACTORY)) {
                        return false;
                    }
                }
            }
            if (++sessions == REQUIRED_SESSIONS) {
                return true;
            }
        }
        return false;
    }
}
