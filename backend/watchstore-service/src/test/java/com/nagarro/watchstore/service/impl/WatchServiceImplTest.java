package com.nagarro.watchstore.service.impl;
import com.nagarro.watchstore.dao.WatchDao;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.WatchService;
import com.nagarro.watchstore.service.impl.WatchServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test cases for WatchServiceImpl class.
 * Author: Karan
 */
class WatchServiceImplTest {

    @Mock
    private WatchDao watchDao;

    @InjectMocks
    private WatchServiceImpl watchService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addWatch_WithNonExistingModel_ShouldReturnSavedWatch() {
        // Arrange
        Watch watch = new Watch();
        watch.setModelNumber("123");
        when(watchDao.findById("123")).thenReturn(Optional.empty());
        when(watchDao.save(watch)).thenReturn(watch);

        // Act
        Watch result = watchService.addWatch(watch);

        // Assert
        verify(watchDao, times(1)).findById("123");
        verify(watchDao, times(1)).save(watch);
        Assertions.assertEquals(watch, result);
    }

    @Test
    void addWatch_WithExistingModel_ShouldThrowBadRequestException() {
        // Arrange
        Watch watch = new Watch();
        watch.setModelNumber("123");
        when(watchDao.findById("123")).thenReturn(Optional.of(watch));

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> watchService.addWatch(watch));
        verify(watchDao, times(1)).findById("123");
        verify(watchDao, never()).save(any());
    }

    @Test
    void getallWatch_WithExistingWatches_ShouldReturnListOfWatches() {
        // Arrange
        List<Watch> watches = new ArrayList<>();
        watches.add(new Watch());
        watches.add(new Watch());
        when(watchDao.findAll()).thenReturn(watches);

        // Act
        List<Watch> result = watchService.getallWatch();

        // Assert
        verify(watchDao, times(1)).findAll();
        Assertions.assertEquals(watches, result);
    }

    @Test
    void getallWatch_WithNoExistingWatches_ShouldThrowNotFoundException() {
        // Arrange
        when(watchDao.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> watchService.getallWatch());
        verify(watchDao, times(1)).findAll();
    }

    @Test
    void getWatchByModel_WithExistingModel_ShouldReturnWatch() {
        // Arrange
        Watch watch = new Watch();
        watch.setModelNumber("123");
        when(watchDao.findById("123")).thenReturn(Optional.of(watch));

        // Act
        Watch result = watchService.getWatchByModel("123");

        // Assert
        verify(watchDao, times(1)).findById("123");
        Assertions.assertEquals(watch, result);
    }

    @Test
    void getWatchByModel_WithNonExistingModel_ShouldThrowNotFoundException() {
        // Arrange
        when(watchDao.findById("123")).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> watchService.getWatchByModel("123"));
    }

    @Test
    void updateWatch_WithExistingModel_ShouldReturnUpdatedWatch() {
        // Arrange
        Watch existingWatch = new Watch();
        existingWatch.setModelNumber("123");
        existingWatch.setWatchBrand("Brand1");
        existingWatch.setPrice(new BigDecimal("100.0"));

        Watch updatedWatch = new Watch();
        updatedWatch.setModelNumber("123");
        updatedWatch.setWatchBrand("Brand2");
        updatedWatch.setPrice(new BigDecimal("200.0"));

        when(watchDao.findById("123")).thenReturn(Optional.of(existingWatch));
        when(watchDao.save(existingWatch)).thenReturn(existingWatch);

        // Act
        Watch result = watchService.updateWatch("123", updatedWatch);

        // Assert
        verify(watchDao, times(1)).findById("123");
        verify(watchDao, times(1)).save(existingWatch);
        Assertions.assertEquals(updatedWatch.getWatchBrand(), result.getWatchBrand());
        Assertions.assertEquals(updatedWatch.getPrice(), result.getPrice());
    }

    @Test
    void updateWatch_WithNonExistingModel_ShouldThrowNotFoundException() {
        // Arrange
        Watch updatedWatch = new Watch();
        updatedWatch.setModelNumber("123");

        when(watchDao.findById("123")).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> watchService.updateWatch("123", updatedWatch));
        verify(watchDao, times(1)).findById("123");
        verify(watchDao, never()).save(any());
    }

    @Test
    void searchWatch_WithExistingQuery_ShouldReturnListOfWatches() {
        // Arrange
        String query = "watch";
        List<Watch> watches = new ArrayList<>();
        watches.add(new Watch());
        watches.add(new Watch());
        when(watchDao.searchWatch(query)).thenReturn(watches);

        // Act
        List<Watch> result = watchService.searchWatch(query);

        // Assert
        verify(watchDao, times(1)).searchWatch(query);
        Assertions.assertEquals(watches, result);
    }

    @Test
    void searchWatch_WithNonExistingQuery_ShouldThrowNotFoundException() {
        // Arrange
        String query = "watch";
        when(watchDao.searchWatch(query)).thenReturn(new ArrayList<>());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> watchService.searchWatch(query));
        verify(watchDao, times(1)).searchWatch(query);
    }

    @Test
    void getBrand_WithExistingBrands_ShouldReturnListOfBrands() {
        // Arrange
        List<String> brands = new ArrayList<>();
        brands.add("Brand1");
        brands.add("Brand2");
        when(watchDao.getBrand()).thenReturn(brands);

        // Act
        List<String> result = watchService.getBrand();

        // Assert
        verify(watchDao, times(1)).getBrand();
        Assertions.assertEquals(brands, result);
    }

    @Test
    void getBrand_WithNoExistingBrands_ShouldThrowNotFoundException() {
        // Arrange
        when(watchDao.getBrand()).thenReturn(new ArrayList<>());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> watchService.getBrand());
        verify(watchDao, times(1)).getBrand();
    }

  
}
