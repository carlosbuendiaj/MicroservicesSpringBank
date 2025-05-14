package com.carlosb.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(
        name="Response",
        description="Schema to hold sucessful response information"
)
@Data @AllArgsConstructor
public class ResponseDto {
    @Schema(
            description="Status code in the response"
    )
    private String statusCode;
    @Schema(
            description="Message in the response"
    )
    private String statusMsg;
}
