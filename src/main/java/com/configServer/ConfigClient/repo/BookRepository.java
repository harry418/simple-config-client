package com.configServer.ConfigClient.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.configServer.ConfigClient.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
