#!/usr/bin/ruby
#Result 96852

require 'json'

$sum = 0

def read_array(json_array)
  all_number_sum = 0
  json_array.each do |a|
    if a.instance_of? Array
      all_number_sum += read_array(a)
    elsif a.instance_of? Hash
      all_number_sum += read_object(a)
    elsif a.instance_of? Fixnum
      all_number_sum += a
    end
  end
  all_number_sum
end

def read_object(json_object)
  all_number_sum = 0
  json_object.keys.each do |a|
    json_value = json_object[a]
    if json_value.instance_of? Array
      all_number_sum += read_array(json_value)
    elsif json_value.instance_of? Hash
      all_number_sum += read_object(json_value)
    elsif json_value.instance_of? String and json_value == 'red'
      all_number_sum = 0
      break
    elsif json_value.instance_of? Fixnum
      all_number_sum += json_value
    end
  end
  all_number_sum
end


File.readlines('input.txt').each { |line|
  JSON json = JSON.parse(line)
  if json.instance_of? Array
    puts read_array(json)
  elsif json.instance_of? Hash
    puts read_object(json)
  end
}