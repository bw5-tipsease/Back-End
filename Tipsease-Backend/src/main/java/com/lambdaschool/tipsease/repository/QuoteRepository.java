package com.lambdaschool.tipsease.repository;

import com.lambdaschool.tipsease.models.Quote;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long>
{

}
