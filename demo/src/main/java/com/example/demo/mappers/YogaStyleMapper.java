package com.example.demo.mappers;

import com.example.demo.domain.YogaClassType;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaStyleDTO;
import org.springframework.stereotype.Component;

@Component
public class YogaStyleMapper {

    public static YogaStyleDTO toDTO(YogaStyle yogaStyle) {
        return new YogaStyleDTO(
                yogaStyle.getClassType()
        );
    }

    public static YogaStyle toEntity(YogaStyleDTO yogaStyleDTO) {
        YogaStyle yogaStyle = new YogaStyle();
        yogaStyle.setClassType(yogaStyleDTO.getClassType());
        return yogaStyle;
    }

    public static YogaStyleDTO fromTypeToDTO(YogaClassType yogaClassType) {
        return new YogaStyleDTO(yogaClassType);
    }

    public static YogaStyle fromClassToStyle(YogaClassType yogaClassType) {
        YogaStyle yogaStyle = new YogaStyle();
        yogaStyle.setClassType(yogaClassType);
        return yogaStyle;
    }
}
