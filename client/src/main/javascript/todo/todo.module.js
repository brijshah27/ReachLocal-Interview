import angular from 'angular';
import TodoComponent from "./todo.component.js";
const TodoModule = angular.module("todo", [])
    .component("todo", TodoComponent)
    .name;

export {TodoModule as default};