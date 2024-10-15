SELECT word, COUNT(*) as total
FROM word_locations
JOIN words ON words.id = word_ref
GROUP BY word
ORDER BY total DESC