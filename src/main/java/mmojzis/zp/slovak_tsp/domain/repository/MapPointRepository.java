package mmojzis.zp.slovak_tsp.domain.repository;

import mmojzis.zp.slovak_tsp.domain.entity.MapPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapPointRepository extends JpaRepository<MapPoint, String> {

    @Query("SELECT p FROM MapPoint p ORDER BY LOWER(TRIM(p.city))")
    List<MapPoint> findAllPoints();
}
