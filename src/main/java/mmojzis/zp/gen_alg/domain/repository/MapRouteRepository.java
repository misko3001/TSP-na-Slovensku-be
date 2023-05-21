package mmojzis.zp.gen_alg.domain.repository;

import mmojzis.zp.gen_alg.domain.entity.MapRoute;
import mmojzis.zp.gen_alg.domain.entity.MapRouteDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapRouteRepository extends JpaRepository<MapRoute, MapRouteDefinition> {

    @Query("SELECT r FROM MapRoute r " +
            "WHERE (r.startPoint = ?1 AND r.endPoint = ?2) " +
            "OR (r.startPoint = ?2 AND r.endPoint = ?1)")
    Optional<MapRoute> findById(String startPoint, String endPoint);

    @Query("SELECT r.distance FROM MapRoute r " +
            "WHERE (r.startPoint = ?1 AND r.endPoint = ?2) " +
            "OR (r.startPoint = ?2 AND r.endPoint = ?1)")
    Optional<Double> findDistanceById(String startPoint, String endPoint);

    @Query(value = "SELECT NEW MapRoute(r.startPoint, r.endPoint, r.distance) FROM MapRoute r")
    List<MapRoute> getBaseRoutes();

}
