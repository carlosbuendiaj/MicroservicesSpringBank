package com.carlosb.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account info"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Carlos"
    )
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @NotEmpty(message = "Name cannot be null or empty")
    private String name;

    @Schema(
            description = "Customer email address",
            example = "Carlos@empresa.com"
    )
    @Email(message = "Email address is not valid")
    @NotEmpty(message = "Email address cannot be null or empty")
    private String email;

    @Schema(
            description = "Customer mobile Number",
            example = "674571968"
    )
    @Pattern(regexp = "(^$|[0-9]{9})", message = "Mobile number be 9 digits")
    private String mobileNumber;


    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
