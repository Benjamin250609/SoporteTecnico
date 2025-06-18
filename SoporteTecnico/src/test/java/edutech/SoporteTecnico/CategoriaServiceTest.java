package edutech.SoporteTecnico;


import edutech.SoporteTecnico.Model.Categoria;
import edutech.SoporteTecnico.Repository.CategoriaRepository;
import edutech.SoporteTecnico.Services.CategoriaServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServices categoriaServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Categoria cat1 = new Categoria(1, "Hardware");
        Categoria cat2 = new Categoria(2, "Software");

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        var result = categoriaServices.findAll();

        assertEquals(2, result.size());
        assertEquals("Hardware", result.get(0).getDescripcion_categoria());
        verify(categoriaRepository).findAll();
    }

    @Test
    void testFindById_CategoriaExiste() {
        Categoria cat = new Categoria(1, "Redes");
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(cat));

        Categoria result = categoriaServices.findById(1);

        assertNotNull(result);
        assertEquals("Redes", result.getDescripcion_categoria());
        verify(categoriaRepository).findById(1);
    }

    @Test
    void testFindById_CategoriaNoExiste() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoriaServices.findById(99);
        });

        assertEquals("Categoria no encontrada", exception.getMessage());
        verify(categoriaRepository).findById(99);
    }
}
