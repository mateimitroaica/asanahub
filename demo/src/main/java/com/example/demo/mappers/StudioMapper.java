package com.example.demo.mappers;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.dto.StudioDTO;
import com.example.demo.dto.YogaClassDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudioMapper {
    public StudioDTO toDTO(Studio studio) {
        String name = studio.getName();
        String location = studio.getLocation();
        List<YogaClassDTO> yogaClassDTOList = studio.getYogaClasses().stream()
                .map(YogaClassMapper::toDto)
                .toList();
        return new StudioDTO(name, location, yogaClassDTOList);
    }

    public Studio toEntity(StudioDTO studioDTO) {
        Studio studio = new Studio();
        String name = studioDTO.getName();
        String location = studioDTO.getLocation();
        List<YogaClass> yogaClasses = studioDTO.getYogaClasses().stream()
                .map(YogaClassMapper::toEntity)
                .toList();
        studio.setName(name);
        studio.setLocation(location);
        studio.setYogaClasses(yogaClasses);
        return studio;
    }
}
