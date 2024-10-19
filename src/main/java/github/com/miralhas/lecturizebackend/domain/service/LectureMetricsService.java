package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.MetricNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Metric;
import github.com.miralhas.lecturizebackend.domain.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureMetricsService {

    private final LectureService lectureService;
    private final MetricRepository metricRepository;

    public Metric findMetricByIdOrException(Long id) {
        return metricRepository.findById(id).orElseThrow(() -> new MetricNotFoundException(id));
    }

    public Metric findLectureMetrics(Long id) {
        lectureService.getLectureOrException(id);
        return findMetricByIdOrException(id);
    }

    @Transactional
    public void updateLectureTimesVisited(Long id) {
        var metric = findMetricByIdOrException(id);
        metric.timesVisitedPlusOne();
    }

    @Transactional
    public void updateLectureTimesShared(Long id) {
        var metric = findMetricByIdOrException(id);
        metric.timesSharedPlusOne();
    }
}
