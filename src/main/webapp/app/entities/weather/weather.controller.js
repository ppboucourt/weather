(function() {
    'use strict';

    angular
        .module('weatherApp')
        .controller('WeatherController', WeatherController);

    WeatherController.$inject = ['Weather', 'WeatherSearch'];

    function WeatherController(Weather, WeatherSearch) {

        var vm = this;

        vm.weathers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Weather.query(function(result) {
                vm.weathers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            WeatherSearch.query({query: vm.searchQuery}, function(result) {
                vm.weathers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
