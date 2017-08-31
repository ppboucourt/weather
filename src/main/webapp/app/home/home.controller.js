(function() {
    'use strict';

    angular
        .module('weatherApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'DTOptionsBuilder', 'DTColumnBuilder'];

    function HomeController ($scope, Principal, LoginService, $state, DTOptionsBuilder, DTColumnBuilder) {
        var vm = this;

        vm.submit = submit;
        vm.validateCity = validateCity;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.form = {};
        vm.cityWeather = {};
        vm.weather = {};
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        // loadAll();
        // function loadAll() {
        //     var url = 'http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/Miami.json';
        //     $.post( url, function( data ) {
        //         console.log(data);
        //         vm.weather = data.result;
        //     });
        // }

        function submit() {
            var url = 'http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/Miami.json';
            if ($('#file').val()) {
                alert('It is ok...');
            }else {
                alert('Upload Empty');
                $.post( url, function( data ) {
                    console.log(data);
                    vm.cityWeather = data.result;
                });
            }
        }

        function validateCity() {
            if (vm.cities && vm.cities.length > 3) {
                var myregexp = /^([^,]+),[^(]*\(([^()]+)\)/;
                var match = myregexp.exec(vm.cities);
                if (match != null) {
                    vm.city = match[1];
                }
            }
        }



        //     "city": "Chanute",
        //     "state": "KS",
        //     "country": "US",
        //     "country_iso3166":"US",
        //     "country_name":"USA",
        //     "zmw": "66720.1.99999",


        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            var url = 'http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/Miami.json';
            $.post( url, function( data ) {
                console.log(data);
                vm.weather = data.result;
                defer.resolve(data.result);
            });
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('city').withTitle('City'),
            DTColumnBuilder.newColumn('state').withTitle('State'),
            DTColumnBuilder.newColumn('country_name').withTitle('Country'),
            DTColumnBuilder.newColumn('zmw').withTitle('ZMW')
        ];
    }
})();
