class Person

  def initialize(name, speed, run_time, rest_time)
    @name = name
    @speed = speed
    @run_time = run_time
    @rest_time = rest_time
    @total_distance= 0
    @points = 0
  end

  attr_accessor :name, :speed, :run_time, :rest_time, :total_distance, :points

  def add_point
    @points += 1
  end

  def move(distance)
    @total_distance += distance
  end

  def cycle
    run_time + rest_time
  end

end