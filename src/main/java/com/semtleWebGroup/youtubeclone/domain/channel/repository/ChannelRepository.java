package com.semtleWebGroup.youtubeclone.domain.channel.repository;

import com.semtleWebGroup.youtubeclone.domain.channel.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    List<Channel> findByTitleContaining(String key);
    boolean existsByTitle(String title);
}
