package com.majority.repository;

import com.majority.model.Image;
import com.majority.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
