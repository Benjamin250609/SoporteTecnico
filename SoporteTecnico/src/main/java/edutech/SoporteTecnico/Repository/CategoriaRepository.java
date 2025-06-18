package edutech.SoporteTecnico.Repository;

import edutech.SoporteTecnico.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("SELECT c FROM Categoria c WHERE c.id_categoria = :id")
    Categoria findCategoriaById(@Param("id") Integer id);
}