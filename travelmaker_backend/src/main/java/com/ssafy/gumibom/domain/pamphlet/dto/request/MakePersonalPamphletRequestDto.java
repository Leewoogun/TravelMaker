package com.ssafy.gumibom.domain.pamphlet.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakePersonalPamphletRequestDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String title;

    private ArrayList<String> categories;

}
