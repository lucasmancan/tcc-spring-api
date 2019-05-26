package br.com.lucasmancan.services;

import br.com.lucasmancan.exceptions.AppNotFoundException;
import br.com.lucasmancan.models.Article;
import br.com.lucasmancan.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService extends AbstractService<Article> {

	@Autowired
	private ArticleRepository repository;

	@Override
	public Article save(Article entity) {
		return repository.save(entity);
	}

	@Override
	public void remove(Article entity) {
		repository.delete(entity);
	}

	@Override
    public Page<Article> findAll(Pageable pageable) {
        return repository.findAll(pageable);
	};

	@Override
	public Article findByCode(Long code) throws AppNotFoundException {
		return repository.findById(code).orElseThrow(() -> new AppNotFoundException());
	}

	@Override
	public Article findById(Long id) throws AppNotFoundException {
		return repository.findById(id).orElseThrow(() -> new AppNotFoundException());
	}


	public List<Article> findAll() {
		return repository.findAll();
	}
}
