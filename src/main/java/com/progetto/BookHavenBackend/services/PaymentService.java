package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.PaymentInformation;
import com.progetto.BookHavenBackend.repositories.PaymentInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PaymentService {
    @Autowired
    PaymentInformationRepository paymentInformationRepository;
    public List<PaymentInformation> getAllPaymentMethod(String userId){
        return paymentInformationRepository.findAllByUserId(userId);
    }
}
