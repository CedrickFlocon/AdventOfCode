#!/usr/bin/ruby
#Result 17836115

require 'matrix'

class Matrix
  def []=(row, column, value)
    @rows[row][column] = value
  end
end

File.open('input.txt', 'r') do |f|

  matrix = Matrix.build(1000) { 0 }
  origin, destination = [0, 0]

  f.each_line do |line|

    coordinates = line.scan(/\d+/)
    origin = [coordinates[0].to_i, coordinates[1].to_i]
    destination = [coordinates[2].to_i, coordinates[3].to_i]

    if line.include? 'turn on'
      action = Proc.new { |x| x+1 }
    elsif line.include? 'turn off'
      action = Proc.new { |x| x == 0 ? 0 : x-1 }
    elsif line.include? 'toggle'
      action = Proc.new { |x| x+2 }
    end

    for i in origin[0]..destination[0]
      for j in origin[1]..destination[1]
        matrix[i, j] = action.call(matrix[i, j])
      end
    end

  end

  puts matrix.inject { |sum, x| sum + x }
end