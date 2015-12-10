#!/usr/bin/ruby
# Result 1588178

File.open('input.txt', 'r') do |f|
  total_square_feet = 0
  square_feet = 0
  l, w, h = 0

  f.each_line do |line|

    dimensions = line.split('x')

    l = dimensions[0].to_i
    w = dimensions[1].to_i
    h = dimensions[2].to_i

    square_feet = (2*l*w + 2*w*h + 2*h*l) + [l, w, h].sort[0..1].inject{|a,b| a*b}
    total_square_feet += square_feet
  end
  puts total_square_feet
end