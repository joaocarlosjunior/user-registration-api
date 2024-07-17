package com.userregistration.domain.entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "tbl_confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdAt = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
