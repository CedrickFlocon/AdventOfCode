#!/usr/bin/ruby
# Result 3783758

File.open('input.txt', 'r') do |f|
  total_ribbon_feet = 0
  ribbon_feet = 0
  l, w, h = 0

  f.each_line do |line|

    dimensions = line.split('x')

    l = dimensions[0].to_i
    w = dimensions[1].to_i
    h = dimensions[2].to_i

    ribbon_feet = [l, w, h].sort[0..1].inject{|a,b| 2*a+2*b} + [l, w, h].inject{|t,a| t*a}
    total_ribbon_feet += ribbon_feet
  end
  puts total_ribbon_feet
end