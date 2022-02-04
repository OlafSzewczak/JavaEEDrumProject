package pl.edu.ug.oszewczak.javaeedrumproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.Salesman;

@Repository
public interface SalesmanRepository extends CrudRepository<Salesman, Long> {
}
