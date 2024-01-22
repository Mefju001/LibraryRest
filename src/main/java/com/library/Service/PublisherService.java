package com.library.Service;

import com.library.Entity.Genre;
import com.library.Entity.Publisher;
import com.library.Repository.BookRepository;
import com.library.Repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findPublisherByPublisherNameIs(String publisherName) {
        return publisherRepository.findPublisherByPublisherNameIs(publisherName);
    }

    @Transactional
    public Publisher save(Publisher Publisher)
    {
        return publisherRepository.save(Publisher);
    }
}
