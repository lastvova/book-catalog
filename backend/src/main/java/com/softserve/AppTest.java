package com.softserve;


import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Slf4j
public class AppTest {
    private static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("BasicEntities");
    }

    public static void main(String[] args) {

        log.info("test");
//        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        Book book = new Book();
////        book.setId(12);
//        book.setIsbn(BigInteger.valueOf(111));
//        book.setName("-----");
//        book.setYearPublisher(LocalDate.of(1993, 2, 13));
//        book.setPublisher("1123");
//        book.setCreateDate(LocalDateTime.now());
//
//        Author author = new Author();
//        author.setCreateDate(LocalDateTime.now());
//        author.setFirstName("11111");
//        author.setSecondName("111111");
//
//        book.addAuthor(author);
//        Book book1 = new Book();
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(author);
//        entityManager.merge(book);
//        entityManager.getTransaction().commit();
//
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(book);
//        entityManager.merge(book);
//        entityManager.persist(book);
//        entityManager.persist(author);
//        entityManager.getTransaction().commit();
//
//        entityManager.close();
//        entityManagerFactory.close();
    }
}
