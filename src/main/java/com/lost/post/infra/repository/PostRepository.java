package com.lost.post.infra.repository;

import com.lost.post.controller.request.Location;
import com.lost.post.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM post "
            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(',"
            + ":#{#southeast.latitude}, ' ', :#{#southeast.longitude}, ',',"
            + ":#{#southwest.latitude}, ' ', :#{#southwest.longitude}, ',',"
            + ":#{#northeast.latitude}, ' ', :#{#northeast.longitude}, ',',"
            + ":#{#northwest.latitude}, ' ', :#{#northwest.longitude}, ')')), post.location)", nativeQuery = true)
    List<Post> findAllByPolygon(
            @Param("southeast") Location southeastLocation,
            @Param("southwest") Location southwestLocation,
            @Param("northeast") Location northeastLocation,
            @Param("northwest") Location northwestLocation);
}
