package entity.auth;

import lombok.*;

/**
 * @author George Karampelas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
