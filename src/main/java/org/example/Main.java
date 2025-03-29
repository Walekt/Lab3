package org.example;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.*;
import java.util.Set;
import java.io.FileReader;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {

        try {
            FileReader reader = new FileReader("books.json");
            org.example.Gson gson = new Gson();
            Person[] visitors = gson.fromJson(reader, Person[].class);

            // Задание 1
            List<Person> visitorList = List.of(visitors);
            System.out.println("Visitors:");
            visitorList.forEach(System.out::println);
            System.out.println("Total visitors: " + visitorList.size());

            // Задание 2
            Set<Book> uniqueBooks = visitorList.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .collect(Collectors.toSet());

            System.out.println("Unique books: " + uniqueBooks);
            System.out.println("Total unique books: " + uniqueBooks.size());

            //Задание 3
            List<Book> sortedBooks = uniqueBooks.stream()
                    .sorted(Comparator.comparingInt(Book::getPublishingYear))
                    .toList();

            System.out.println("Books sorted by publishing year: " + sortedBooks);

            // Задание 4
            boolean hasBook = visitorList.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .anyMatch(book -> "Jane Austen".equals(book.getAuthor()));

            System.out.println("Is there a book by Jane Austen: " + hasBook);

            // Задание 5: Максимальное число книг в избранном у одного посетителя
            int maxFavoriteBooks = visitorList.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                    .max()
                    .orElse(0);

            System.out.println("Maximum number of favorite books: " + maxFavoriteBooks);
        } catch (FileNotFoundException e){
            System.out.println("File books.json not found");
        }

    }
}