package homemate.mapper.building;
import homemate.domain.admin.AdminEntity;
import homemate.domain.building.BuildingEntity;
import homemate.dto.building.BuildingDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BuildingMapper {

    /**
     * Entity -> Dto
     */
    BuildingDto.BuildingResponseDto toResponseDto(BuildingEntity buildingEntity);

    /**
     * Dto -> Entity
     */
    @Mapping(target="id", ignore = true)
    BuildingEntity toResponseEntity(BuildingDto.BuildingResponseDto buildingResponseDto);


    @Mapping(target="id", ignore = true)
    BuildingEntity toReqeustEntity(BuildingDto.BuildingRequestDto buildingRequestDto, AdminEntity adminEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //@Mapping(target="id", ignore = true)
//    @Mapping(target="createAt", ignore = true)
//    @Mapping(target="modifiedAt", ignore = true)
    @Mapping(target="id", ignore = true)
    void updateFromPatchDto(BuildingDto.BuildingPatchDto buildingPatchDto, @MappingTarget BuildingEntity buildingEntity);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    //@Mapping(target="id", ignore = true)
////    @Mapping(target="createAt", ignore = true)
////    @Mapping(target="modifiedAt", ignore = true)
//    @Mapping(target="id", ignore = true)
//    void updateFromAdminPatchDto(BuildingDto.AdminPatchBuildingDto adminPatchBuildingDto, @MappingTarget BuildingEntity buildingEntity);

}

