var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/api/open', function(req, res, next){
    console.log(req.body)
    res.json({'drawer': req.body.drawer})
})

router.get('/api/test/open', function(req, res){
    myObj = [{
        test: 1
    }]
    res.json(myObj)
})

module.exports = router;
