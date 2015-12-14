angular.module('myApp123',[]);

//Login panel
angular.module('myApp123').controller('loginController', function($rootScope, $scope,$http){
	//on load
	$rootScope.loginFlag=true;
	//on click
	$scope.login = function(){
	$http.post("coupon/customer/login?name="+$scope.name+"&password="+$scope.password).success(function(response){
		if(response=="Successful Customer Login!! Welcome "+$scope.name+"!!"){
			alert($scope.loginResult=response);
			$rootScope.myname=$scope.name;
			$rootScope.loginFlag=false;
			$rootScope.customerFlag=true;
			$rootScope.header="All of Customer's Coupons:";
			$http.get("coupon/customer/all").success(function(response){
				if(response.couponHTML[0].id=="-1"){
					$rootScope.error=response.couponHTML[0].message;
				}
				else{
					$rootScope.allCoupons=response.couponHTML;
				}
			});
		}	
		else{
			alert($scope.loginResult=response);
			$scope.loginResult=response;
		}
	});
	
	};
});



//customer controller
angular.module('myApp123').controller('customerController', function($rootScope,$scope,$http){
	
	//logout
	$scope.logout = function(){
		$http.post("coupon/customer/logout").success(function(response){});
		$rootScope.loginFlag=true;
		$rootScope.customerFlag=false;
		$rootScope.storeFlag=false;
		window.location="index.html";
	};

	//get by type Coupon
	$scope.showType=function(){
		$rootScope.typeFlag=true;//){$rootScope.typeFlag=false;}else {$rootScope.typeFlag=true;}
		$rootScope.priceFlag=false;
	};
	//get by price Coupon
	$scope.showPrice=function(){
		$rootScope.priceFlag=true;//){$rootScope.priceFlag=false;}else {$rootScope.priceFlag=true;}
		$rootScope.typeFlag=false;
	};
	
	//showAllCoupons
	$scope.all=function(){
		$http.get("coupon/customer/all").success(function(response){
			if(response.couponHTML[0].id=="-1"){
				$rootScope.error=response.couponHTML[0].message;
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.header="All of Customer's Coupons:";
				$rootScope.error="";
			}
		});
		$rootScope.typeFlag=false;
		$rootScope.priceFlag=false;
	};
	
	//show Coupon Store
	$scope.store=function(){
		if($rootScope.storeFlag==true){
			$rootScope.storeFlag=false;
			$rootScope.error="Thank you very much for visiting our Coupon Store!! [Coupon Systems Inc.]";
		}else{
			$http.get("coupon/customer/store").success(function(response){
				if(response.couponHTML[0].id=="-1"){
					$rootScope.storeError=response.couponHTML[0].message;
					$rootScope.allStore="";
				}
				else{
					$rootScope.allStore=response.couponHTML;
					$rootScope.storeError="";
				}
			});
			$rootScope.storeFlag=true;
			$rootScope.typeFlag=false;
			$rootScope.priceFlag=false;			
		}
	};
});


angular.module('myApp123').controller('storeController', function($rootScope,$scope, $http){
	//Purchase
	$scope.purchase=function(x,p){
		//if(confirm($scope.confirm=("Are you sure you would like to Purchase Coupon #"+x+"(Purchase can't be un-done and your money will automaticly be transfered to the Coupon's Company)?"))==true)
		{
			$http.get("coupon/customer/purchase?cid="+x).success(function(response){
				if(response=="ok"){
					$http.get("rest/income/purchase/"+$rootScope.myname+"/"+p).success(function(response){
						
					});
					
				
					//$scope.storeError=response;
					$http.get("coupon/customer/store").success(function(response){
						if(response.couponHTML[0].id=="-1"){
							$rootScope.storeError=response.couponHTML[0].message;
							$rootScope.allStore="";
						}
						else{
							$rootScope.allStore=response.couponHTML;
							$rootScope.storeError="";
						}
					});
					$http.get("coupon/customer/all").success(function(response){
						if(response.couponHTML[0].id=="-1"){
							$rootScope.error=response.couponHTML[0].message;
							$rootScope.allCoupons="";
						}
						else{
							$rootScope.error="";
							$rootScope.allCoupons=response.couponHTML;
						}
					});
					if($rootScope.error=="" && $rootScope.storeError=="")
						$rootScope.error="Coupon was Successfully Purchased!!";
				}else
					$scope.storeError=response;
			});
		}
	};
});

//specified show controllers
angular.module('myApp123').controller('typeController', function($rootScope,$scope, $http){
	$scope.type=function(){
		$http.get("coupon/customer/type?type="+$scope.stype).success(function(response){
			if(response==null){
				$rootScope.allCoupons="";
				$rootScope.error="You don't have any Coupons with the Type '"+$scope.stype+"'.";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				//$rootScope.typeFlag=false;
				$rootScope.header="Selected Coupon/s by Type ("+$scope.stype+"):";
				$rootScope.error="";
			}
		});
	};
});

angular.module('myApp123').controller('priceController', function($rootScope,$scope, $http){
	$scope.price=function(){
		$http.get("coupon/customer/price?price="+$scope.sprice).success(function(response){
			if(response==null){
				$rootScope.allCoupons="";
				$rootScope.error="You don't have any Coupons that are priced under "+$scope.sprice+"nis.";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				//$rootScope.priceFlag=false;
				$rootScope.header="Selected Coupon/s by Maximum Price (up to "+$scope.sprice+"nis):";
				$rootScope.error="";
			}
		});
	};
});