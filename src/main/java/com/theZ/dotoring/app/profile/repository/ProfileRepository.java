package com.theZ.dotoring.app.profile.repository;

import com.theZ.dotoring.app.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
