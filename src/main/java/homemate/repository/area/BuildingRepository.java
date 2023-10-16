package homemate.repository.area;
import homemate.domain.area.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity,Long> {

    @Query("SELECT b FROM BuildingEntity b WHERE b.address LIKE %:keyword% ")
    List<BuildingEntity> findKeyword(String keyword);

}
