package com.example.demo_service.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_service.DemoDto;

@Repository
public interface DemoRepository extends JpaRepository<DemoDto, Integer> {

}
