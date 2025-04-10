package com.example.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendId implements Serializable{
    static final long serialVersionUID = 1L;
    Long user;
    Long friend;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        return Objects.equals(user, friendId.user) &&
            Objects.equals(friend, friendId.friend);
    }
    @Override
    public int hashCode() {
        return Objects.hash(user, friend);
    }
}
