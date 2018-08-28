package pl.dms.dmsbackend.dataInput;

import lombok.Data;

@Data
public class NewRequestDTO {
    private String title;
    private String category;
    private String description;
}

