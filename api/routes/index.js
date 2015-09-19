var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/api/open', function(req, res, next){
    res.json(req.body.drawer)
})

module.exports = router;
