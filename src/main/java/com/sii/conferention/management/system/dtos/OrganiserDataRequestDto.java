package com.sii.conferention.management.system.dtos;

import com.sii.conferention.management.system.enums.OrganiserDataRequestTypeEnum;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganiserDataRequestDto {
    private UserDataDto userDataDto;
    private OrganiserDataRequestTypeEnum dataRequestType;
}
