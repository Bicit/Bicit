require 'test_helper'

class EventsControllerTest < ActionController::TestCase
  setup do
    @event = events(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:events)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create event" do
    assert_difference('Event.count') do
      post :create, event: { asistentes: @event.asistentes, autor: @event.autor, distancia: @event.distancia, duracion: @event.duracion, fecha_evento: @event.fecha_evento, fecha_publicacion: @event.fecha_publicacion, nombre: @event.nombre, privacidad: @event.privacidad, tal_ves: @event.tal_ves }
    end

    assert_redirected_to event_path(assigns(:event))
  end

  test "should show event" do
    get :show, id: @event
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @event
    assert_response :success
  end

  test "should update event" do
    patch :update, id: @event, event: { asistentes: @event.asistentes, autor: @event.autor, distancia: @event.distancia, duracion: @event.duracion, fecha_evento: @event.fecha_evento, fecha_publicacion: @event.fecha_publicacion, nombre: @event.nombre, privacidad: @event.privacidad, tal_ves: @event.tal_ves }
    assert_redirected_to event_path(assigns(:event))
  end

  test "should destroy event" do
    assert_difference('Event.count', -1) do
      delete :destroy, id: @event
    end

    assert_redirected_to events_path
  end
end
