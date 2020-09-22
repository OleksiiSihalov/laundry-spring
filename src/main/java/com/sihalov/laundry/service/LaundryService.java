package com.sihalov.laundry.service;

import com.sihalov.laundry.data.entity.Laundry;
import com.sihalov.laundry.repository.LaundryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaundryService {
    private final LaundryRepository laundryRepository;

    public LaundryService(LaundryRepository laundryRepository) {
        this.laundryRepository = laundryRepository;
    }

    public List<Laundry> getAll() {
        return laundryRepository.findAll();
    }

    public Laundry getLaundry(Long id) {
        return laundryRepository.getOne(id);
    }

    public void createLaundry(Laundry laundry){
        laundryRepository.save(laundry);
    }

    public void editLaundry(Long id, Laundry changedLaundry){
        Laundry laundry = getLaundry(id);
        laundry.setName(changedLaundry.getName());
        laundry.setDescription(changedLaundry.getDescription());
        laundry.setPhoneNumber(changedLaundry.getPhoneNumber());
        laundry.setEmail(changedLaundry.getEmail());
        laundry.setAddress(changedLaundry.getAddress());
        laundry.setLatitude(changedLaundry.getLatitude());
        laundry.setLongitude(changedLaundry.getLongitude());
        laundry.setServicesList(changedLaundry.getServicesList());
        laundry.setOrdersList(changedLaundry.getOrdersList());
        laundryRepository.save(laundry);
    }

    public void deleteLaundry(Long id){
        laundryRepository.deleteById(id);
    }

}
