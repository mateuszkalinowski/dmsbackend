package pl.dms.dmsbackend.utils.dataInput;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
