package mmojzis.zp.gen_alg.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import mmojzis.zp.gen_alg.domain.entity.MapPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MapFeatureServiceTest {

    public static final Long NUMBER_OF_POINTS = 200L;

    public static final Long NUMBER_OF_ROUTES = (NUMBER_OF_POINTS * (NUMBER_OF_POINTS - 1)) / 2;

    @Autowired
    private MapFeatureService mapFeatureService;

    @Test
    void serviceHasToReturnTheCorrectNumberOfPoints() {
        assertThat(mapFeatureService.countAllPoints())
                .isEqualTo(NUMBER_OF_POINTS);
    }

    @Test
    void serviceReturnsNoDuplicatePoints() {
        assertThat(mapFeatureService.getPoints().stream().distinct().count())
                .isEqualTo(NUMBER_OF_POINTS);
    }

    @Test
    void serviceHasTheCorrectAmountOfRoutes() {
        assertThat(mapFeatureService.countAllRoutes())
                .isEqualTo(NUMBER_OF_ROUTES);
    }

    @Test
    void serviceIsAbleToRetrieveAllPossibleRoutes() {
        List<MapPoint> points = mapFeatureService.getPoints();
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            MapPoint current = points.get(i);
            for (int j = i + 1; j < NUMBER_OF_POINTS; j++) {
                int nextIndex = j;
                String city = current.getCity();
                assertThatNoException()
                        .isThrownBy(() -> mapFeatureService.getRoute(city, points.get(nextIndex).getCity()));
            }
        }
    }



}
