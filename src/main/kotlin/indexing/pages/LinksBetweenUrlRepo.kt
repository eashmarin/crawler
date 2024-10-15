package org.example.indexing.pages

import org.example.indexing.pages.model.LinkBetweenUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinksBetweenUrlRepo : CrudRepository<LinkBetweenUrl, Long>