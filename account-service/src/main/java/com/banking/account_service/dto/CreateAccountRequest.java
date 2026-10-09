package com.banking.account_service.dto;

import com.banking.account_service.entity.AccountType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "Account holder name reuired")
    private String accountHolderName;

    @NotBlank(message = "email required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "phone number reuired")
    private String phone;

    @NotNull(message = "Account Type is required")
    private AccountType accountType;

    @NotNull(message = "Reuired initial Deposit")
    @Positive(message = "Initial Deposit must be positive")
    private BigDecimal initialDeposit;

}
