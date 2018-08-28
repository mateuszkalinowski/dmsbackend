package pl.dms.dmsbackend.dataOutput;

import lombok.Data;

@Data
public class InhabitantDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    Integer roomNumber;
}
