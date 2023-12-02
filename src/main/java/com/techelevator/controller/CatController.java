package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CatCard> list(){
        try {
          return catCardDao.getCatCards();
        }
        catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Broken request");
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CatCard get(@PathVariable int id){
        try {
            return catCardDao.getCatCardById(id);
        }
        catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat card not found.");
        }
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard random(){
        CatCard newCard = new CatCard();
        newCard.setCatFact(catFactService.getFact().getText());
        newCard.setImgUrl(catPicService.getPic().getFile());

        return newCard;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public CatCard create(@RequestBody CatCard catCard){
        return catCardDao.createCatCard(catCard);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CatCard update(@RequestBody CatCard catCard, @PathVariable int id){
        catCard.setCatCardId(id);
        try {
            CatCard updateCatCard = catCardDao.updateCatCard(catCard);
            return updateCatCard;
        }
        catch (DaoException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat card not found.");
        }
    }

}
