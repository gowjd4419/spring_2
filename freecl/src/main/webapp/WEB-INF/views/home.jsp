<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<!DOCTYPE html>

<html>
<head>
<style>

   header{
   background: lightgray;
   height: 215px;
   }
   main{
   background: #f2f4f7;
   min-height:700px;
   }
   footer{
     background: darkgray;
     height: 310px;
   }

	.dropdown{
	  position : relative;
	  display : inline-block;
	}
	
	.dropbtn_icon{
	  font-family : 'Material Icons';
	}
	.dropbtn{
	  border : none;
	  border-radius : 4px;
	  background-color: #f5f5f5;
	  font-weight: 400;
	  color : rgb(37, 37, 37);
	  padding : 12px;
	  width :200px;
	  text-align: left;
	  cursor : pointer;
	  font-size : 12px;
	}
	.dropdown-content{
	  display : none;
	  position : absolute;
	  z-index : 1; /*다른 요소들보다 앞에 배치*/
	  font-weight: 400;
	  background-color: #f9f9f9;
	  min-width : 200px;
	}
	
	.dropdown-content a{
	  display : block;
	  text-decoration : none;
	  color : rgb(37, 37, 37);
	  font-size: 12px;
	  padding : 12px 20px;
	}
	
	.dropdown-content a:hover{
	  background-color : #ececec
	}
	
	.dropdown:hover .dropdown-content {
	  display: block;
	}
	h1{
	  bold:10px;
	}
</style>
</head>
<body>
<title>Freecl</title>
<header>
   <h1>Freecl</h1>

    <div class="dropdown">
      <span class="dropbtn">베스트</span>
    </div> 
    <div class="dropdown">
       <span class="dropbtn">신상</span>
    </div>
    <div class="dropdown">
       <span class="dropbtn">아우터</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">상의</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">셔츠</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">트레이닝</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">베이직</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">원피스</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">스커트</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">팬츠</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">가방</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">신발</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
    <div class="dropdown">
       <span class="dropbtn">악세사리</span>
       <div class="dropdown-content">
        <a href="#">자켓</a>
        <a href="#">조끼</a>
        <a href="#">패딩</a>
      </div>
    </div>
  </header>
  <main>메인
  <img src="images/cloths/jpg"/>
  </main>
     
  <footer>하단</footer>
    
    
    
    
    

</body>
</html>
