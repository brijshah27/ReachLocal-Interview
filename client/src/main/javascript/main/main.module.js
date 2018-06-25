import angular from 'angular';
import MainComponent from "./main.component.js";
import TodoModule from "../todo/todo.module.js";
import TodoService from "./todo.service.js";
import NgResource from 'angular-resource';

const MainModule = angular.module("main", [TodoModule, NgResource])
    .component("main", MainComponent)
    .service('todoService', TodoService)
    .name;

export {MainModule as default};