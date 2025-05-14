package com.carlosb.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account info"
)
public class AccountsDto {


    @Schema(
            description = "Account Number",
            example = "1234567890"
    )
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the bank",
            example = "Saving"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Bank branch Address",
            example = "123 False Street"
    )
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;

}
